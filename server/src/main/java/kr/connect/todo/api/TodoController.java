package kr.connect.todo.api;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.connect.todo.obj.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController{
	
	private final Logger log = LoggerFactory.getLogger(TodoController.class);
	private final TodoService service;
	
	@Autowired
	public TodoController(TodoService service){
		this.service = service;
	}
	
	@GetMapping
	Collection<Todo> readList(){
		return  service.selectAll();
	}
	
	@GetMapping("/countCompletedTodos")
	int countCompletedTodos(){
		return service.countCompletedTodos();
	}
	
	@GetMapping("/{id}")
	Todo read(@PathVariable Integer id){
		return service.selectById(id);
	}
	@GetMapping("insert/{todoText}")
	Integer insert(@PathVariable String todoText){
		Todo todo = new Todo(todoText, 0, new Date());
		int id = service.insertTodo(todo);
		return id;
	}
	@GetMapping("delete/{id}")
	boolean delete(@PathVariable Integer id){
		return service.deleteById(id);
	}
	@GetMapping("complete/{id}/{completed}")
	boolean complete(@PathVariable Integer id, @PathVariable Integer completed){
		return service.completeTodo(id, completed);
	}
	
}
