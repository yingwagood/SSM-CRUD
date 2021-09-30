package com.wang.crud.service;

import com.wang.crud.bean.Employee;
import com.wang.crud.bean.EmployeeExample;
import com.wang.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    public Boolean checkUser(String empName) {
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long l = employeeMapper.countByExample(employeeExample);
        return l==0;
    }

    public Employee getEmp(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public int deletById(Integer id) {
     return  employeeMapper.deleteByPrimaryKey(id);
    }

    public int deletByIds(List<Integer> integerList) {
        EmployeeExample example = new EmployeeExample();
       example.createCriteria().andEmpIdIn(integerList);
        return employeeMapper.deleteByExample(example);
    }
}
