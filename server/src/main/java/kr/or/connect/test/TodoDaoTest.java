package kr.or.connect.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.connect.todo.api.TodoController;
import kr.connect.todo.obj.Todo;
import kr.or.connect.todo.AppConfig;
import kr.or.connect.todo.persistence.TodoDao;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class TodoDaoTest {
//TodoDao 동작 테스트용 Test Class
	//테스트요 테스트
	//로그를 통한 값 확인
	private final Logger log = LoggerFactory.getLogger(TodoDaoTest.class);
	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldTodoCount() {
		int count = dao.countTodos();
		log.info("count : " + count);
	}
	
	@Test
	public void shouldTodoInsertAndSelect() {
		Todo todo = new Todo("memo data", 0, new Date());
		Integer newId = dao.insertTodo(todo);
		log.info("id : " + newId);
		log.info("selected id : " + dao.selectById(newId));
	}
	
	@Test
	public void shouldTodoDelete() {

		Todo todo = new Todo("delete data", 0, new Date());
		Integer id = dao.insertTodo(todo);

		int affected = dao.deleteById(id);
	}
	
	@Test
	public void shouldTodoUpdate() {
		// Given
		Todo todo = new Todo("update check data", 0, new Date());
		Integer id = dao.insertTodo(todo);

		// When
		todo.setId(id);
		todo.setTodo("update completed");
		int affected = dao.updateTodo(todo);

	}
}