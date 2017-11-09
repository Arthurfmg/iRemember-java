var hostSeries = "http://localhost:8080/iRemember/services/";

function listarSeries() {
	$("#seriesGrid").html("");
	var usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
		
	$.ajax({		
		url : hostSeries + 'series/usuario/' + usuarioLogado.id,
		type : 'GET',
		contentType : 'application/json',
		success : function(data) {			
			if ($.isArray(data.series.link)) {
			   for ( var i = 0; i < data.series.link.length; i++) {
			      var link = data.series.link[i]['@href'];
			      segueLinkSerie(link);
			   }
			} else {
			   var link = data.series.link['@href'];
			   segueLinkSerie(link);
			}
		},
		error : function() {
			alert("Erro ao carregar séries do usuário.");
		}
	});
	
}


function segueLinkSerie(link){	
  $.ajax({
      url : hostSeries + link,
      type : 'GET',
      success : function(data) {
    	  adicionaSerieNovaAoGrid(data.serie);
      },
      error : function(data) {
    	  alert("erro");
      }
   });
}

function adicionaSerieNovaAoGrid(serie) {
   var data = 	"<tr>"
		      	+ "<td>" + serie.id + "</td>"
		      	+ "<td>" + serie.nome + "</td>"
		      	+ "<td>" + serie.descricao + "</td>"
		      	+ "<td>" + serie.numeroCapitulos + "</td>"
		      	+ "<td>" + serie.numeroTemporadas + "</td>"
		      	+ "<td>" + serie.numeroCapituloParou + "</td>"
		      	+ "<td>" + serie.numeroTemporadaParou + "</td>"
		      	+ "<td width='5%'><img src='icons/pencil.png' width='20px' height='20px' style='cursor: pointer' data-toggle='modal' data-target='#editarModal' onClick='carregarSerieParaEdicao(\""+serie.nome+"\")'></td>"
		      	+ "<td width='5%'><img src='icons/sign-delete-icon.png' width='20px' height='20px' style='cursor: pointer' onClick='excluir(\""+serie.nome+"\")'></td>"
		      + "</tr>";

   $("#seriesGrid").append(data);
}

function adicionarSerie(){
	var usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
	
	var data = $("#novaSerieForm").serializeJSON();
	data = "{\"serie\":" + JSON.stringify(data) + "}";
	//alert(data);
	
	//var parse = JSON.parse(data);
	
	$(".square").show();
	
	$.ajax({
	   url : hostSeries + 'series/usuario/' + usuarioLogado.id,
	   type : 'POST',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		  $("#novaSerieForm")[0].reset();
		  listarSeries();
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
			url : hostSeries + 'series/' + nome,
			type : 'DELETE',
			success : function(data) {
				$("#novaSerieForm")[0].reset();
				  listarSeries();
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

function carregarSerieParaEdicao(nome) {
	  $.ajax({
	      url : hostSeries + 'series/' + nome,
	      type : 'GET',
	      success : function(data) {
	    	  var serie = data.serie;
	    	  
	    	  $("#editarId").val(serie.id);
	    	  $("#editarNome").val(serie.nome);
	    	  $("#editarDescricao").val(serie.descricao);
	    	  $("#editarnCapitulos").val(serie.numeroCapitulos);
	    	  $("#editarnTemporadas").val(serie.numeroTemporadas);
	    	  $("#editarnUltimoCapituloAssistido").val(serie.numeroCapituloParou);
	    	  $("#editarnUltimaTemporadaAssistida").val(serie.numeroTemporadaParou);
	    	  
	    	  $("#editarModalNomeSerie").html(serie.nome);
	      },
	      error : function(data) {
	    	  alert("erro ao carregar serie pra edição!");
	      }
	   });
}

function salvarAlteracoes(){
	var data = $("#editarSerieForm").serializeJSON();
	data = "{\"serie\":" + JSON.stringify(data) + "}";	
	
	$(".square").show();
			
	$.ajax({
	   url : hostSeries + 'series/',
	   type : 'PUT',
	   contentType : 'application/json',
	   data : data,
	   success : function(data) {
		   $('#editarModal').modal('toggle');
		   
		   $("#editarSerieForm")[0].reset();
			  listarSeries();
			  $(".square").hide();
	   },
	   error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " "+ data.statusText);
		
	   }
	 });

}