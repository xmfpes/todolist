package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String SELECT_ALL =
			"SELECT *FROM todo ORDER BY date DESC";
	//최신 할일이 위로 가도록 날짜 기준 내림차순 정렬
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String COUNT_TODO = 
			"SELECT COUNT(*) FROM todo";
	static final String SELECT_BY_ID =
			"SELECT id, todo, completed, date FROM todo where id = :id";
	static final String UPDATE_TODO =
			"UPDATE todo SET\n"
			+ "todo = :todo "
			+ "WHERE id = :id";	
	static final String COMPLTED_TODO =
			"UPDATE todo SET\n"
			+ "completed = :completed "
			+ "WHERE id = :id";	
	static final String COUNT_COMPLETED_TODO =
			"SELECT COUNT(*) FROM todo\n"
			+ "WHERE completed = 0";
}
