package com.yihe.framework.ehr;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;
@Repository
public class EhrRepository {
    private final JdbcTemplate jdbc;
    public EhrRepository(javax.sql.DataSource source){this.jdbc=new JdbcTemplate(source);}
    public List<Map<String,Object>> all(String sql,Object... args){return jdbc.query(sql,args,(r,n)->{
        Map<String,Object> m=new LinkedHashMap<>();ResultSetMetaData d=r.getMetaData();
        for(int i=1;i<=d.getColumnCount();i++){int t=d.getColumnType(i);m.put(d.getColumnLabel(i),t==Types.DATE||t==Types.TIMESTAMP?r.getString(i):r.getObject(i));}return m;});}
    public Map<String,Object> one(String sql,Object...args){List<Map<String,Object>> a=all(sql,args);return a.isEmpty()?null:a.get(0);}
    public int exec(String sql,Object...args){return jdbc.update(sql,args);}
    public long count(String sql,Object...args){Number n=jdbc.queryForObject(sql,args,Number.class);return n==null?0:n.longValue();}
    public boolean organizationWithin(String owner,String unit){
        Set<String> visited=new HashSet<>();
        while(unit!=null&&visited.size()<64&&visited.add(unit)){
            if(owner.equals(unit))return true;
            Map<String,Object> row=one("SELECT Super_Org_Code parent FROM yh_org_tree WHERE Org_Code=? AND COALESCE(Is_Del,0)=0",unit);
            if(row==null||row.get("parent")==null)return false;
            unit=String.valueOf(row.get("parent"));
        }
        return false;
    }
    public void insert(String table,Map<String,Object> fields){
        if(!table.matches("a_[a-z_]+"))throw new IllegalArgumentException();
        StringJoiner cols=new StringJoiner(","),params=new StringJoiner(",");
        fields.forEach((k,v)->{if(!k.matches("[a-z][a-z0-9_]*"))throw new IllegalArgumentException();cols.add(k);params.add("?");});
        exec("INSERT INTO "+table+" ("+cols+") VALUES ("+params+")",fields.values().toArray());
    }
}
