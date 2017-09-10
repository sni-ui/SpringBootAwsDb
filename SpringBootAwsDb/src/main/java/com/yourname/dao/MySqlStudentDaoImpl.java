package com.yourname.dao;

import com.yourname.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository("mysql")
public class MySqlStudentDaoImpl implements StudentDao{


    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static class StudentsRowMapper implements RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet resultset, int i) throws SQLException {
            Student student = new Student();
            student.setId(resultset.getInt("id"));
            student.setName(resultset.getString("name"));
            student.setCourse(resultset.getString("course"));
            return student;
        }
    }

    @Override
    public Collection<Student> getAllStudents() {
         final String sql = "SELECT id, name, course FROM students";
         List<Student> students = jdbcTemplate.query(sql, new StudentsRowMapper());
             return students;
         }
    @Override
    public Student getStudentById(int id) {
        final String sql = "SELECT id, name, course FROM students where id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new StudentsRowMapper(), id);
        return student;
    }




    @Override
    public void removeStudentById(int id){
        final String sql = "DELETE FROM students Where id=?";
        jdbcTemplate.update( sql, id);
    }

    @Override
    public void updateStudent(Student student){
        final String sql = "UPDATE students SET name = ?, course = ? WHERE id = ?";
        final int id = student.getId();
        final String name = student.getName();
        final String course = student.getCourse();
        jdbcTemplate.update(sql, new Object[]{name, course, id});
    }

    @Override
    public void insertStudentToDb(Student student){
    final String sql = "INSERT INTO students (name, course)VALUES(?,?)";
    final String name = student.getName();
    final String course = student.getCourse();
    jdbcTemplate.update(sql, new Object[] {name, course});

    }
}

