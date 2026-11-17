from  fastapi import FastAPI
from app.schemas import AnalysisRequest, AnalysisResponse
from app.analyzer import analyze

app = FastAPI(title="Deskmin AI service")

@app.get("/health")
def health_check():
    return {"status": "ok"}

@app.post("/analyze", response_model=AnalysisResponse)
def analyze_ticket(request: AnalysisRequest):
    return analyze(request.title, request.description)
    
