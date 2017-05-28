package kr.or.connect.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import kr.connect.todo.obj.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	private final TodoDao dao;
	
	@Autowired
	public TodoService(TodoDao dao){
		this.dao = dao;
	}
	
	public Integer insertTodo(Todo todo) {
		Integer id = dao.insertTodo(todo);
		return id;
	}
	public int countTodos() {
		Integer count = dao.countTodos();
		return count;
	}
	
	public int countCompletedTodos(){
		Integer count = dao.countCompletedTodos();
		return count;
	}
	public List<Todo> selectAll(){
		return dao.selectAll();
	}
	
	public Todo selectById(Integer id){
		return dao.selectById(id);
	}
	
	public boolean deleteById(Integer id) {
		int affected = dao.deleteById(id);
		return affected == 1;
	}
	
	public boolean updateTodo(Todo todo) {
		int affected = dao.updateTodo(todo);
		return affected == 1;
	}
	
	public boolean completeTodo(Integer id, Integer completed) {
		int affected = dao.completeTodo(id, completed);
		return affected == 1;
	}
}
