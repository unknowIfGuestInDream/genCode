
    /**
     * 
     */
    @${mappingType}("${method}")
    @Log
    public Map<String, Object> ${method}(${inParams}) {
        return BaseUtils.success(${module?uncap_first}Service.${method}(${outParams}));
    }