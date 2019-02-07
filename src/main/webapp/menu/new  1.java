	public String downloadFileUtil() throws Exception {
		String docId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("docId");
		LOGGER.info("download start1:::" + docId);
		long id = Long.parseLong(docId);
		Documents doc = null;
		File file = null;
		try {

			doc = (Documents) documentsImpl.getModelById(Integer.parseInt(docId), "DocId");
			// LOGGER.info("doc location >>"+doc.getDocumentLoc());
			if (doc != null) { 
				LOGGER.info("download start:::");
				String filename = doc == null ? null : doc.getOriginalFileName();
				String orgfilename = doc == null ? null : doc.getSysFilename();

				file = new File(doc.getDocumentLoc() + "/" + orgfilename); 

				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
						.getContext();
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				String contentType = servletContext.getMimeType(file.getName());

				if (contentType == null) {
					contentType = "application/octet-stream";
				}
				writeOutContent(response, file, filename);
				FacesContext.getCurrentInstance().responseComplete();
				LOGGER.info("download done::: for " + filename + ":: file");
			} else {
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				response.setStatus(401);
				FacesContext.getCurrentInstance().responseComplete();
			}
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage() + ex);
			LOGGER.info("download exception::" + ex);
			throw ex;
		}
		return null;
	}