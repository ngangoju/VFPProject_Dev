package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;

import tres.dao.interfc.IEvaluation;

import tres.domain.Evaluation;

public class EvaluationImpl extends AbstractDao<Long, Evaluation> implements IEvaluation {

	public Evaluation saveEvaluation(Evaluation evaluation) {
		return saveIntable(evaluation);
	}

	public List<Evaluation> getListEvaluations() {
		return (List<Evaluation>) (Object) getModelList();
	}

	public Evaluation getEvaluationById(int EvaluationId, String primaryKeyclomunName) {
		return (Evaluation) getModelById(EvaluationId, primaryKeyclomunName);
	}

	public Evaluation UpdateEvaluation(Evaluation evaluation) {
		return updateIntable(evaluation);
	}

}