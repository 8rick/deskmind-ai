import unittest
from unittest.mock import patch

from app.knowledge_base import DocumentChunk, retrieve_context, retrieve_documents


class KnowledgeBaseTest(unittest.TestCase):

    def test_retrieves_relevant_billing_document(self):
        chunks = [
            DocumentChunk("billing.md", "Estorno financeiro é validado em até dois dias úteis."),
            DocumentChunk("access.md", "Nunca solicite senha ou código de autenticação."),
        ]
        with patch("app.knowledge_base._load_chunks", return_value=chunks):
            result = retrieve_documents("estorno cobrança financeiro", top_k=1)

        self.assertEqual(1, len(result))
        self.assertEqual("billing.md", result[0].source)

    def test_returns_empty_when_nothing_matches(self):
        chunks = [DocumentChunk("access.md", "Recuperação de senha e autenticação.")]
        with patch("app.knowledge_base._load_chunks", return_value=chunks):
            result = retrieve_documents("integração pagamento", top_k=3)

        self.assertEqual([], result)

    def test_context_contains_source_name(self):
        chunks = [DocumentChunk("support.md", "Orientar usuário com cordialidade.")]
        with patch("app.knowledge_base.retrieve_documents", return_value=chunks):
            context = retrieve_context("orientação")

        self.assertIn("[Fonte: support.md]", context)
