from  fastapi import FastAPI
from app.schemas import (
    AnalysisCategory,
    AnalysisPriority,
    AnalysisRequest, 
    AnalysisResponse
)

app = FastAPI(title="Deskmin AI service")

@app.get("/health")
def health_check():
    return {"status": "ok"}

@app.post("/analyze", response_model=AnalysisResponse)
def analyze_ticket(request: AnalysisRequest):
   return AnalysisResponse(
        category=AnalysisCategory.GENERAL,
        priority=AnalysisPriority.MEDIUM,
        summary=f"Análise inicial do chamado {request.title}",
        suggested_response="Olá, Recebemos o seu chamado e nossa equipe já está verificando a solicitação.",
        reasoning="Classificação fixa gerada pelo serviço Python.",
    )
    
