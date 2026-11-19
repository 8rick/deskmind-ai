"""
Retrieval for DeskMind's small internal Markdown knowledge base.

This starter implementation uses BM25 lexical ranking. It avoids embeddings and
a vector database so the first RAG iteration is easy to run locally. Each
paragraph is treated as a chunk; the best matching chunks are sent to the LLM.
"""
from __future__ import annotations

import math
import re
from pathlib import Path
from typing import NamedTuple

KNOWLEDGE_DIR = Path(__file__).resolve().parent.parent / "knowledge_base"
TOP_K = 3


class DocumentChunk(NamedTuple):
    source: str
    content: str


def _tokenize(text: str) -> list[str]:
    return re.findall(r"[a-zA-ZÀ-ÿ0-9_]+", text.lower())


def _load_chunks() -> list[DocumentChunk]:
    chunks: list[DocumentChunk] = []
    if not KNOWLEDGE_DIR.exists():
        return chunks

    for path in sorted(KNOWLEDGE_DIR.glob("*.md")):
        text = path.read_text(encoding="utf-8")
        paragraphs = [
            part.strip()
            for part in re.split(r"\n\s*\n", text)
            if part.strip()
        ]
        for paragraph in paragraphs:
            if len(_tokenize(paragraph)) >= 3:
                chunks.append(DocumentChunk(path.name, paragraph))
    return chunks


def _bm25_score(query_tokens: list[str], documents: list[list[str]], index: int) -> float:
    if not query_tokens or not documents:
        return 0.0

    document_frequency: dict[str, int] = {}
    for tokens in documents:
        for token in set(tokens):
            document_frequency[token] = document_frequency.get(token, 0) + 1

    total_documents = len(documents)
    average_length = sum(map(len, documents)) / total_documents or 1
    current = documents[index]
    frequencies: dict[str, int] = {}
    for token in current:
        frequencies[token] = frequencies.get(token, 0) + 1

    k1, b = 1.5, 0.75
    score = 0.0
    for token in query_tokens:
        frequency = frequencies.get(token, 0)
        if frequency == 0:
            continue
        df = document_frequency.get(token, 0)
        inverse_frequency = math.log(1 + (total_documents - df + 0.5) / (df + 0.5))
        denominator = frequency + k1 * (1 - b + b * len(current) / average_length)
        score += inverse_frequency * (frequency * (k1 + 1)) / denominator
    return score


def retrieve_documents(query: str, top_k: int = TOP_K) -> list[DocumentChunk]:
    """Return up to top_k relevant paragraphs, highest score first."""
    if top_k <= 0:
        return []

    chunks = _load_chunks()
    tokenized_documents = [_tokenize(chunk.content) for chunk in chunks]
    query_tokens = _tokenize(query)
    ranked = [
        (_bm25_score(query_tokens, tokenized_documents, index), index)
        for index in range(len(chunks))
    ]
    ranked = [(score, index) for score, index in ranked if score > 0]
    ranked.sort(key=lambda item: item[0], reverse=True)
    return [chunks[index] for _, index in ranked[:top_k]]


def retrieve_context(query: str, top_k: int = TOP_K) -> str:
    """Format retrieved chunks with source names for the LLM prompt."""
    documents = retrieve_documents(query, top_k)
    return "\n\n".join(
        f"[Fonte: {document.source}]\n{document.content}"
        for document in documents
    )
