package com.newangels.gen.service.impl.dataBaseInfo;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.service.DataBaseInfoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 无数据库实现类
 * gen.nodb=false
 *
 * @author: TangLiang
 * @date: 2021/6/19 13:21
 * @since: 1.0
 */
@Service
@ConditionalOnProperty(name = "gen.isdb", havingValue = "false")
public class NoDataBaseInfoServiceImpl implements DataBaseInfoService {
    //数据源集合
    private static final List<Map<String, Object>> DataBaseInfoList = new ArrayList<>();

    @PostConstruct
    public void initDataBaseInfoList() {
        Map<String, Object> map = new HashMap<>();
        map.put("ID", BaseUtils.getUuid());
        map.put("NAME", "MYSQL_TEST");
        map.put("URL", "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&useOldAliasMetadataBehavior=true");
        map.put("DRIVER", "com.mysql.jdbc.Driver");
        map.put("USERNAME", "root");
        map.put("PASSWORD", "mysql");
        map.put("CREATE_TIME", LocalDateTime.now());
        map.put("UPDATE_TIME", LocalDateTime.now());
        DataBaseInfoList.add(map);
    }

    @Override
    public Map<String, Object> loadDataBaseInfo(String ID) {
        List<Map<String, Object>> list = DataBaseInfoList.stream()
                .filter(m -> m.get("ID").equals(ID))
                .collect(Collectors.toList());
        return list.size() > 0 ? list.get(0) : new HashMap<>();
    }

    @Override
    public List<Map<String, Object>> selectDataBaseInfo() {
        return DataBaseInfoList;
    }

    @Override
    public int insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        Map<String, Object> map = new HashMap<>();
        map.put("ID", BaseUtils.getUuid());
        map.put("NAME", NAME);
        map.put("URL", URL);
        map.put("DRIVER", DRIVER);
        map.put("USERNAME", USERNAME);
        map.put("PASSWORD", PASSWORD);
        map.put("CREATE_TIME", LocalDateTime.now());
        DataBaseInfoList.add(map);
        return 1;
    }

    @Override
    public int updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        for (Map<String, Object> map : DataBaseInfoList) {
            String id = (String) map.get("ID");
            if (id.equals(ID)) {
                map.put("NAME", NAME);
                map.put("URL", URL);
                map.put("DRIVER", DRIVER);
                map.put("USERNAME", USERNAME);
                map.put("PASSWORD", PASSWORD);
                map.put("UPDATE_TIME", LocalDateTime.now());
                break;
            }
        }
        return 1;
    }

    @Override
    public int deleteDataBaseInfo(String ID) {
        for (int i = 0; i < DataBaseInfoList.size(); i++) {
            Map<String, Object> map = DataBaseInfoList.get(i);
            String id = (String) map.get("ID");
            if (id.equals(ID)) {
                DataBaseInfoList.remove(i);
                break;
            }
        }
        return 1;
    }
}