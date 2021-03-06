package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFile(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{id}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata, filedate) " +
            "VALUES(#{name}, #{contentType}, #{size}, #{userId}, #{data}, #{saveDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{id}")
    int deleteFile(Integer fileId);
}
