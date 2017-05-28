package kr.or.connect.todo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.connect.todo.api.TodoController;
import kr.connect.todo.obj.Todo;
import kr.or.connect.todo.persistence.TodoDao;

public class TodoLauncher {
	//예제를 보고 만든 테스트 클래스, 테스트 기능은 Junit으로 넘겨 사용하지 않는 클래스
	//Test Class Not used(Junit create)
	private final static Logger log = LoggerFactory.getLogger(TodoLauncher.class);
	public static void main(String[] args){
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TodoDao dao = context.getBean(TodoDao.class);
		int count = dao.countTodos();
		log.info("count : " + count);
		
		Todo todo = dao.selectById(1);
		log.info(todo.toString());
		
		Todo todo2 = new Todo("메모입니다", 0, new Date());
		Integer newId = dao.insertTodo(todo2);
		log.info("id : " + newId);
		log.info("selected id" + dao.selectById(newId));
		
		context.close();
	}
}
