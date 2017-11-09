var hostColecoes = "http://localhost:8080/iRemember/services/";

function listarColecoes() {
	$("#colecoesGrid").html("");
	var usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
		
	$.ajax({		
		url : hostColecoes + 'colecoes/usuario/' + usuarioLogado.id,
		type : 'GET',
		contentType : 'application/json',
		success : function(data) {			
			if ($.isArray(data.colecoes.link)) {
			   for ( var i = 0; i < data.colecoes.link.length; i++) {
			      var link = data.colecoes.link[i]['@href'];
			      segueLinkColecao(link);
			   }
			} else {
			   var link = data.colecoes.link['@href'];
			   segueLinkColecao(link);
			}
		},
		error : function() {
			alert("Erro ao carregar coleções do usuário.");
		}
	});
}


function segueLinkColecao(link){	
  $.ajax({
      url : hostColecoes + link,
      type : 'GET',
      success : function(data) {
    	  adicionaColecaoNovaAoGrid(data.colecao);
      },
      error : function(data) {
    	  alert("erro");
      }
   });
}

function adicionaColecaoNovaAoGrid(colecao) {
   var data = 	"<tr>"
		      	+ "<td width='5%'>" + colecao.id + "</td>"
		      	+ "<td width='40%'>" + colecao.nome + "</td>"
		      	+ "<td width='40%'>" + colecao.descricao + "</td>"
		      	+ "<td width='5%'><a href='#' class='btn btn-outline-danger my-2 my-sm-0' role='button' aria-pressed='true' onClick='verUnidades(\""+colecao.nome+"\");'>Ver unidades</a></td>"
		      	+ "<td width='5%'><img src='icons/pencil.png' width='20px' height='20px' style='cursor: pointer' data-toggle='modal' data-target='#editarModal' onClick='carregarColecaoParaEdicao(\""+colecao.nome+"\")'></td>"
		      	+ "<td width='5%'><img src='icons/sign-delete-icon.png' width='20px' height='20px' style='cursor: pointer' onClick='excluir(\""+colecao.nome+"\")'></td>"
		      + "</tr>";

   $("#colecoesGrid").append(data);
}

function adicionarColecao(){
	var usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
	
	var data = $("#novaColecaoForm").serializeJSON();
	data = "{\"colecao\":" + JSON.stringify(data) + "}";
	
	var parse = JSON.parse(data);
	
	$(".square").show();
	
	$.ajax({
	   url : hostColecoes + 'colecoes/usuario/' + usuarioLogado.id,
	   type : 'POST',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		  $("#novaColecaoForm")[0].reset();
		  listarColecoes();
		  $(".square").hide();
	   },
	   error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);
	   }
	 });
}

function excluir(nome){
	if(confirm("Deseja realmente excluir esse registro?")){
	
		$(".square").show();
		
		$.ajax({
			url : hostColecoes + 'colecoes/' + nome,
			type : 'DELETE',
			success : function(data) {
				$("#novaColecaoForm")[0].reset();
				  listarColecoes();
				  $(".square").hide();
			},
			error : function(data) {
				console.log(data);
				alert("Ocorreu um erro: " + data.status + " "
						+ data.statusText);
			}
		});
	}
}

function carregarColecaoParaEdicao(nome) {
	  $.ajax({
	      url : hostColecoes + 'colecoes/' + nome,
	      type : 'GET',
	      success : function(data) {
	    	  var colecao = data.colecao;
	    	  
	    	  $("#editarId").val(colecao.id);
	    	  $("#editarNome").val(colecao.nome);
	    	  $("#editarDescricao").val(colecao.descricao);
	    	  
	    	  $("#editarModalNomeColecao").html(colecao.nome);
	      },
	      error : function(data) {
	    	  alert("erro ao carregar colecao pra edição!");
	      }
	   });
}

function salvarAlteracoes(){
	var data = $("#editarColecaoForm").serializeJSON();
	data = "{\"colecao\":" + JSON.stringify(data) + "}";	
	
	$(".square").show();
			
	$.ajax({
	   url : hostColecoes + 'colecoes/',
	   type : 'PUT',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		   $('#editarModal').modal('toggle');
		   
		   $("#editarColecaoForm")[0].reset();
			  listarColecoes();
			  $(".square").hide();
	   },
	   error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " "+ data.statusText);
	   }
	 });

}

function verUnidades(colecao_nome){
	//Setar o colecao_id no hidden do unidades.html
	
	sessionStorage.setItem("colecao_nome", colecao_nome);		
	window.location.href = baseHost + "unidades.html";
}