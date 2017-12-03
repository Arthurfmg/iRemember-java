var hostUnidades = "http://localhost:8080/iRemember/services/";

function listarUnidades() {
	$("#unidadesGrid").html("");
	
	$.ajax({		
		url : hostUnidades + 'unidades/colecao/' + $("#colecao_id").val(),
		type : 'GET',
		contentType : 'application/json',
		success : function(data) {			
			if ($.isArray(data.unidades.link)) {
			   for ( var i = 0; i < data.unidades.link.length; i++) {
			      var link = data.unidades.link[i]['@href'];
			      segueLinkUnidade(link);
			   }
			} else {
			   var link = data.unidades.link['@href'];
			   segueLinkUnidade(link);
			}
		},
		error : function() {
			alert("Erro ao carregar unidades da coleção.");
		}
	});
}


function segueLinkUnidade(link){	
  $.ajax({
      url : hostUnidades + link,
      type : 'GET',
      success : function(data) {
    	  adicionaUnidadeNovaAoGrid(data.unidade);
      },
      error : function(data) {
    	  alert("erro");
      }
   });
}

function adicionaUnidadeNovaAoGrid(unidade) {
   var data = 	"<tr>"
		      	+ "<td width='5%'>" + unidade.id + "</td>"
		      	+ "<td width='5%'>" + unidade.numero + "</td>"
		      	+ "<td width='40%'>" + unidade.nome + "</td>"
		      	+ "<td width='40%'>" + unidade.descricao + "</td>"
		      	+ "<td width='5%'><img src='icons/pencil.png' width='20px' height='20px' style='cursor: pointer' data-toggle='modal' data-target='#editarModal' onClick='carregarUnidadeParaEdicao(\""+unidade.nome+"\")'></td>"
		      	+ "<td width='5%'><img src='icons/sign-delete-icon.png' width='20px' height='20px' style='cursor: pointer' onClick='excluir(\""+unidade.nome+"\")'></td>"
		      + "</tr>";

   $("#unidadesGrid").append(data);
}

function adicionarUnidade(){	
	var data = $("#novaUnidadeForm").serializeJSON();
	data = "{\"unidade\":" + JSON.stringify(data) + "}";
	
	var parse = JSON.parse(data);
	
	$(".square").show();
	
	$.ajax({
	   url : hostUnidades + 'unidades/colecao/' + $("#colecao_id").val(),
	   type : 'POST',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		  $("#novaUnidadeForm")[0].reset();
		  listarUnidades();
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
			url : hostUnidades + 'unidades/' + nome,
			type : 'DELETE',
			success : function(data) {
				$("#novaUnidadeForm")[0].reset();
				  listarUnidades();
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

function carregarUnidadeParaEdicao(nome) {
	  $.ajax({
	      url : hostUnidades + 'unidades/' + nome,
	      type : 'GET',
	      success : function(data) {
	    	  var unidade = data.unidade;
	    	  
	    	  $("#editarId").val(unidade.id);
	    	  $("#editarNumero").val(unidade.numero);
	    	  $("#editarNome").val(unidade.nome);
	    	  $("#editarDescricao").val(unidade.descricao);
	    	  
	    	  $("#editarModalNomeUnidade").html(unidade.nome);
	      },
	      error : function(data) {
	    	  alert("erro ao carregar unidade pra edição!");
	      }
	   });
}

function salvarAlteracoes(){
	var data = $("#editarUnidadeForm").serializeJSON();
	data = "{\"unidade\":" + JSON.stringify(data) + "}";	
	
	$(".square").show();
			
	$.ajax({
	   url : hostUnidades + 'unidades/colecao/' + $("#colecao_id").val(),
	   type : 'PUT',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		   $('#editarModal').modal('toggle');
		   
		   $("#editarUnidadeForm")[0].reset();
			  listarUnidades();
			  $(".square").hide();
	   },
	   error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " "+ data.statusText);
		
	   }
	 });

}

function mostrarNomeColecao(){	
	$("#nomeColecaoUnidade").html(sessionStorage.getItem("colecao_nome"));
	
    	$.ajax({
	      url : hostUnidades + 'colecoes/' + sessionStorage.getItem("colecao_nome"),
	      type : 'GET',
	      success : function(data) {
	    	  $("#colecao_id").val(data.colecao.id);
	    	  listarUnidades();
	      },
	      error : function(data) {
	    	  alert("erro");
	      }
	   });
}
