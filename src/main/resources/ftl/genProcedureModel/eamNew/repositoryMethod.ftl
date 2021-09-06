
    /**
     * 
     */
    public Map<String, Object> ${procedureName}(${inParams}) {
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.execute((CallableStatementCreator) con -> {
            String sql = "{call ${procedureName}(${procedureParams})}";
            CallableStatement statement = con.prepareCall(sql);
${repositoryParam}
          return statement;
        }, cs -> {
            cs.execute();
            Map<String, Object> returnValue = new HashMap<>(8);
${repositoryResult}
          return returnValue;
        });
    }