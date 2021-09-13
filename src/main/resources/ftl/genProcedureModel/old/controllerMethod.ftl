
    /**
     * 
     */
    @RequestMapping(value = "${method}")
    @ResponseBody
    public Map<String, Object> ${method}(${inParams}) {
        return BaseUtils.success(${module?uncap_first}Service.${method}(${outParams}));
    }