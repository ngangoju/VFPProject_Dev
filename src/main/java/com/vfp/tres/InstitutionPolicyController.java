package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.EvaluationImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.InstitutionRegRequestImpl;
import tres.domain.Activity;
import tres.domain.Evaluation;
import tres.domain.Institution;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Users;
import tres.vfp.dto.InstitutionDto;

@SuppressWarnings("unchecked")
@ManagedBean
@ViewScoped
public class InstitutionPolicyController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "InstitutionEscaletePolicy :: InstitutionEscaletePolicy ";
	private static final long serialVersionUID = 1L;

	private int institutionID;
	private boolean renderDiv;
	private boolean isValid;
	private boolean valid;

	private String nmbrTime;
	private Institution institution;
	private Evaluation evaluation;
	private InstitutionEscaletePolicy policy;
	private Users usersSession;

	private List<InstitutionEscaletePolicy> policies = new ArrayList<InstitutionEscaletePolicy>();
	private List<InstitutionRegistrationRequest> validInstitution = new ArrayList<InstitutionRegistrationRequest>();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	JSFBoundleProvider provider = new JSFBoundleProvider();

	InstitutionEscaletPolicyImpl policyImpl = new InstitutionEscaletPolicyImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	EvaluationImpl evalImpl = new EvaluationImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (institution == null) {
			institution = new Institution();
		}
		if (policy == null) {
			policy = new InstitutionEscaletePolicy();
		}
		try {
			policies = policyImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus", "createdBy" },
					new Object[] { ACTIVE, ACCEPTED, usersSession.getViewId() }, "InstitutionRegistrationRequest",
					"instRegReqstDate desc");
			validInstitution = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus", "createdBy" },
					new Object[] { ACTIVE, ACCEPTED, usersSession.getViewId() }, "InstitutionRegistrationRequest",
					"instRegReqstDate desc");

		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public int getNumberFailure(Activity activity) {
		int count = 0;
		try {
			for (Object[] data : evalImpl.reportList(
					"select count(*) from Evaluation e where e.genericStatus='active' and details='failed' and ACTIVITYID="
							+ activity.getActivityId() + "")) {
				count = Integer.parseInt(data[0] + "");
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unused")
	public void createEscaletePolicy() {
		try {
			institution = institutionImpl.getInstitutionById(institutionID, "institutionId");
			InstitutionEscaletePolicy pol = new InstitutionEscaletePolicy();
			pol = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");

			if (pol != null) {
				pol.setGenericStatus(DESACTIVE);
				pol.setUpdatedBy(usersSession.getViewId());
				pol.setUpDtTime(timestamp);
				policyImpl.UpdateInstEscalPolicy(pol);
				savePolicy();
			} else {
				savePolicy();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePolicy() {
		try {
			policy.setCreatedBy(usersSession.getViewId());
			policy.setCrtdDtTime(timestamp);
			policy.setGenericStatus(ACTIVE);
			policy.setUpDtTime(timestamp);
			policy.setReschduleTime(Integer.parseInt(nmbrTime));
			policy.setInstitution(institution);
			policyImpl.saveInstEscalPolicy(policy);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.Policy"));
			LOGGER.info(CLASSNAME + ":::Contact creation failed");
			clearPolicyFuileds();
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Policy Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error" + e.getMessage()));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<InstitutionDto> display(List<InstitutionRegistrationRequest> institutionRegistrationRequests) {

		try

		{
			List<InstitutionDto> dtos = new ArrayList<InstitutionDto>();
			for (InstitutionRegistrationRequest inst : institutionRegistrationRequests) {
				InstitutionDto institutionDto = new InstitutionDto();
				institutionDto.setEditable(false);
				institutionDto.setInstitutionRegDate(inst.getInstitution().getInstitutionRegDate());
				institutionDto.setInstitutionId(inst.getInstitution().getInstitutionId()); 
				dtos.add(institutionDto);
			}
			return dtos;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	public void clearPolicyFuileds() {
		policy = new InstitutionEscaletePolicy();
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public int getInstitutionID() {
		return institutionID;
	}

	public void setInstitutionID(int institutionID) {
		this.institutionID = institutionID;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public List<InstitutionEscaletePolicy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<InstitutionEscaletePolicy> policies) {
		this.policies = policies;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public InstitutionImpl getInstitutionImpl() {
		return institutionImpl;
	}

	public void setInstitutionImpl(InstitutionImpl institutionImpl) {
		this.institutionImpl = institutionImpl;
	}

	public EvaluationImpl getEvalImpl() {
		return evalImpl;
	}

	public void setEvalImpl(EvaluationImpl evalImpl) {
		this.evalImpl = evalImpl;
	}

	public boolean isRenderDiv() {
		return renderDiv;
	}

	public void setRenderDiv(boolean renderDiv) {
		this.renderDiv = renderDiv;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public InstitutionEscaletePolicy getPolicy() {
		return policy;
	}

	public void setPolicy(InstitutionEscaletePolicy policy) {
		this.policy = policy;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public InstitutionEscaletPolicyImpl getPolicyImpl() {
		return policyImpl;
	}

	public void setPolicyImpl(InstitutionEscaletPolicyImpl policyImpl) {
		this.policyImpl = policyImpl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public List<InstitutionRegistrationRequest> getValidInstitution() {
		return validInstitution;
	}

	public void setValidInstitution(List<InstitutionRegistrationRequest> validInstitution) {
		this.validInstitution = validInstitution;
	}

	public String getNmbrTime() {
		return nmbrTime;
	}

	public void setNmbrTime(String nmbrTime) {
		this.nmbrTime = nmbrTime;
	}

	public InstitutionRegRequestImpl getRequestImpl() {
		return requestImpl;
	}

	public void setRequestImpl(InstitutionRegRequestImpl requestImpl) {
		this.requestImpl = requestImpl;
	}

}
