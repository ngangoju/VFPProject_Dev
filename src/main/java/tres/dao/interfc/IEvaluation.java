package tres.dao.interfc;

import java.util.List;

import tres.domain.Evaluation;

public interface IEvaluation {
	public Evaluation saveEvaluation(Evaluation evaluation);

	public List<Evaluation> getListEvaluations();

	public Evaluation getEvaluationById(int EvaluationId, String primaryKeyclomunName);

	public Evaluation UpdateEvaluation(Evaluation evaluation);

}
