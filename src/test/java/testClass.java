import com.github.pagehelper.PageInfo;
import com.wang.crud.bean.Employee;
import com.wang.crud.dao.DepartmentMapper;
import com.wang.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * @program:
 * @Author: ying
 * @Date: 2021/9/14 17:14
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:SpringConfigure.xml", "classpath:SpringMvcConfigure.xml"})
public class testClass {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void test1() {
        Integer integer = 3;
        Byte b = (byte) (integer.intValue());
        System.out.println(b);
    }

    @Test
    public void test2() {
//ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfigure.xml");
//DepartmentMapper mapper = applicationContext.getBean(DepartmentMapper.class);
//System.out.println(departmentMapper);
//departmentMapper.insertSelective(new Department(null,"开发部"));
//departmentMapper.insertSelective(new Department(null,"测试部"));
//departmentMapper.insertSelective(new Department(null,"财务部"));
//System.out.println(employeeMapper);
//employeeMapper.insertSelective(new Employee(null,"wang","1","wang@qq.com",1));
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String name = UUID.randomUUID().toString().substring(0, 5) + i;
            employeeMapper.insertSelective(new Employee(null, name, String.valueOf((int) (Math.random() * 2)), name + "@qq.com", (int) (Math.random() * 4)));

        }

    }


    @Test
    public void test3() {
        for (int i = 0; i < 100; i++) {

            System.out.println((int) (Math.random() * 2));
        }
    }

    @Autowired
    WebApplicationContext context;
    //    模拟mvc请求，获取请求结果
    MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test4() throws Exception {
//        模拟请求得到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pageNum", "1")).andReturn();
        MockHttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("page");
        System.out.println("当前页码"+pageInfo.getPageNum());
    }
}
