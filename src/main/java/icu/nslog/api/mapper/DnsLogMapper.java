package icu.nslog.api.mapper;

import icu.nslog.api.bean.DnsRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DnsLogMapper {

    @Insert("INSERT INTO record(record, source, type) VALUES (#{record.record}, #{record.source}, #{record.type});")
    int recordStore(@Param("record")DnsRecord record);

    @Select("select source, timestamp from record where record=#{domain}")
    @Results(value = {
            @Result(column = "source", property = "source"),
            @Result(column = "timestamp", property = "timestamp")
    })
    List<Map<Object, Object>> qryRecordByDomain(@Param("domain")String domain);

}

