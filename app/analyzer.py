import os
from dotenv import load_dotenv
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI
from app.schemas import AnalysisResponse

load_dotenv(override=True)

SYSTEM_PROMPT = """Você é um especialista em triagem e suporte técnico de TI.
Analise o chamado informado e classifique-o estritamente de acordo com o esquema estruturado solicitado.

Regras obrigatórias:
1. Classifique 'category' e 'priority' usando EXCLUSIVAMENTE os valores permitidos nos enums do esquema. Nunca invente novas categorias ou prioridades.
2. Responda todos os campos textuais ('summary', 'suggested_response' e 'reasoning') em português do Brasil.
3. Não invente fatos, causas, erros ou prazos que não estejam no chamado.
4. Caso a descrição esteja ausente ou vazia, analise usando apenas o título do chamado e informe no 'reasoning' que a classificação foi baseada somente no título."""

prompt = ChatPromptTemplate.from_messages([
    ("system", SYSTEM_PROMPT),
    ("human", "Titulo do chamado: {title}\nDescrição: {description}"),
])

def analyze(title: str, description: str | None) -> AnalysisResponse:
    modelo_name = os.getenv("LLM_MODEL", "openai/gpt-oss-20b")
    
    llm = ChatOpenAI(
        model=modelo_name, 
        base_url="https://api.groq.com/openai/v1",
        api_key=os.getenv("OPEN_API_KEY"),
        temperature=0,
    )

    # Adicione method="function_calling" para evitar conflitos com o esquema da Groq
    structured_llm = llm.with_structured_output(AnalysisResponse, method="function_calling")
    chain = prompt | structured_llm

    clean_description = (
        description.strip()
        if description and description.strip()
        else "Não informado"
    )

    return chain.invoke({
        "title": title,
        "description": clean_description
    })
