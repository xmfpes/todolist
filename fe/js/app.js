
(function (window) {
	'use strict';
	loadAllPage();
	//초기 모든 데이터 표시 (완료 메모, 비완료 메모 전부 표시)

	//Active 메모의 수 카운트
	function countData() {
		$.ajax({
			url: "/api/todos/countCompletedTodos",
			success:function(args){   
				$('.todo-count strong').html(args);
			}
		});
	}

	//Completed 메모 표시
	function loadCompletedPage(){
		$.getJSON("/api/todos",function(data){
			var items = [];
			$('.todo-list').empty();
			$.each(data, function(index) {
				if(data[index].completed == 1){
					$('.todo-list').append(
							"<li id='" + data[index].id + "'>"+
							'<div class="view">'+
							'<input class="toggle" type="checkbox">'+
							'<label>' + data[index].todo + '</label>'+
							'<button class="destroy"></button>'+
							'</div>'+
							'</li>'
					);
					$('#'+ data[index].id).attr('class', 'completed');
					$('#'+ data[index].id).find("input.toggle").attr('checked', true);
				}	
			});
			countData();
		});
	}

	//Active 메모 표시
	function loadActivePage(){
		$.getJSON("/api/todos",function(data){
			var items = [];
			$('.todo-list').empty();
			$.each(data, function(index) {
				if(data[index].completed == 0){
					$('.todo-list').append(
							"<li id='" + data[index].id + "'>"+
							'<div class="view">'+
							'<input class="toggle" type="checkbox">'+
							'<label>' + data[index].todo + '</label>'+
							'<button class="destroy"></button>'+
							'</div>'+
							'</li>'
					);
				}	
			});
			countData();
		});
	}
	//All 메모 표시(Completed + Active)
	function loadAllPage(){

		$.getJSON("/api/todos",function(data){
			var items = [];
			$('.todo-list').empty();
			$.each(data, function(index) {
				$('.todo-list').append(
						"<li id='" + data[index].id + "'>"+
						'<div class="view">'+
						'<input class="toggle" type="checkbox">'+
						'<label>' + data[index].todo + '</label>'+
						'<button class="destroy"></button>'+
						'</div>'+
						'</li>'
				);

				if(data[index].completed == 1){
					$('#'+ data[index].id).attr('class', 'completed');
					$('#'+ data[index].id).find("input.toggle").attr('checked', true);
				}	
			});
			countData();
		});
	}
	//완료 메모 삭제하는 버튼의 Click Event 정의
	$(document).on("click",".clear-completed", function() {
		$( ".todo-list li" ).each(function( index ) {
			var li = $(this);
			if(li.attr('class') == 'completed'){

				var liId = li.attr('id');
				$.ajax({
					url: "/api/todos/delete/" + liId,
					success:function(args){   
						if(args){
							li.remove();
						}else{			
						}
						countData();
					}
				});
			}
		});
	});

	//All Filter로 모든 메모 표시
	$(document).on("click","#filters_all", function() {
		loadAllPage();
		$('.filters').find('li').find('a').removeAttr('class');
		$(this).attr('class', 'selected');
	});

	//Active Filter로 Active 메모 표시
	$(document).on("click","#filters_active", function() {
		loadActivePage();
		$('.filters').find('li').find('a').removeAttr('class');
		$(this).attr('class', 'selected');
	});

	//Completed Filter로 Completed 메모 표시
	$(document).on("click","#filters_completed", function() {
		loadCompletedPage();
		$('.filters').find('li').find('a').removeAttr('class');
		$(this).attr('class', 'selected');
	});

	// Your starting point. Enjoy the ride!
	//Enter key 입력, 메모 추가 null 일 경우 체크
	$( ".new-todo" ).keypress(function( event ) {
		if ( event.which == 13 ) {

			var new_message = $('.new-todo').val();
			if(new_message == ""){
				alert("내용을 입력해주세요!");
			}else{
				$.ajax({
					url: "/api/todos/insert/" + new_message,
					success:function(args){   
						alert("메모가 추가되었습니다.");
						//최신의 할일이 제일 위에 오도록 prepend
						$('.todo-list').prepend(
								"<li id='" + args + "'>"+
								'<div class="view">'+
								'<input class="toggle" type="checkbox">'+
								'<label>' + new_message + '</label>'+
								'<button class="destroy"></button>'+
								'</div>'+
								'</li>'
						);
						countData();
					}
				});
				$('.new-todo').val("");
			}


		}
	});

	//동적으로 추가된 클래스들이 있으므로 이벤트 위임을 사용해 이벤트 핸들러 등록
	//메모의 X 버튼을 클릭할 시 (.destroy) 부모 클래스를 구해 li 삭제 후 ajax 호출
	$(document).on('click', ".destroy", function() {
		var li = $(this).parent("div").parent("li");
		var liId = li.attr("id");
		//클릭한 메모의 Id값을 구함
		$.ajax({
			url: "/api/todos/delete/" + liId,
			success:function(args){   
				if(args){
					alert("메모가 삭제되었습니다.");
					li.remove();
				}else{
					alert("메모 삭제에 실패하였습니다.");				
				}
				countData();
			}
		});
	});

	//체크박스 클릭 시 이벤트 설정 메모가 Completed인 경우 -> Active로 변경
	// Active 인 경우 -> Completed로 변경
	//ajax 호출하여 Completed 상태 변경
	$(document).on('click','input.toggle',  function() {
		var completed = "1";
		//기본 디폴트 값은 complted = 1
		var li = $(this).parent("div").parent("li");
		//만약 메모가 완료된 상태라면, complete 값에 0 대입
		if(li.attr('class') == "completed"){
			completed = "0";
		}
		var liId = li.attr("id");
		$.ajax({
			url: "/api/todos/complete/" + liId + "/" + completed ,
			success:function(args){   
				if(args){
					if(completed == 1){
						li.attr('class', 'completed');
					}else if(completed == 0){
						li.removeAttr('class');		
					}

				}else{	
					alert("메모 업데이트 실패");
				}
				countData();
			}
		});

	});

})(window);
