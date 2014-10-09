
<script type="text/javascript">

$(document).ready(function() {

document.getElementById("paginationDiv").innerHTML='<div id="pages" style="box-shadow: none; border: 0px;"><input id="firstPage" type="button" value="First" /><input id="previousPage" type="button" value="Prev" />Showing Page <input type="text" id="pageNumberSelect" value='+(parseInt(pageNumber)+1) + ' style="width: 50px;"/> of '+numberOfPages +' <input id="nextPage" type="button" value="Next" /> <input id="lastPage" type="button" value="Last" /> with <input type="text" id="maxResultsSelect" value ='+ resultsPerPage +' style="width: 50px;"/> results per page<input id="pageSelectButton" type="button" value="Go" /></div>';

$('#previousPage').on("click",function(){
	var pageNumber= parseInt($('#pageNumber').val());
	if(pageNumber>=1){
		$('#pageNumber').val(pageNumber-1);
		$('#paginationForm').submit();
	}
});

$('#nextPage').on("click",function(){
	var pageNumber= parseInt($('#pageNumber').val());
	if(pageNumber< parseInt(numberOfPages)-1){
		$('#pageNumber').val(pageNumber+1);
		$('#paginationForm').submit();
	}
});

$('#firstPage').on("click",function(){
	$('#pageNumber').val(0);
	$('#paginationForm').submit();
});

$('#lastPage').on("click",function(){
	$('#pageNumber').val(parseInt(numberOfPages)-1);
	$('#paginationForm').submit();
});


$('#pageSelectButton').on("click",function(){
	var pageNumber = $('#pageNumberSelect').val();
	if(!(pageNumber=="")){
		var resultsPerPageSelected = parseInt($('#maxResultsSelect').val());
		if(resultsPerPageSelected>0){
			pageNumber= parseInt($('#pageNumberSelect').val());
			var resultsPerPageDefault = parseInt(resultsPerPage);
			if(resultsPerPageSelected != resultsPerPageDefault){
				$('#dbHitFlag').val("1");
			}
			$('#pageNumber').val(pageNumber-1);
			$('#resultsPerPage').val(resultsPerPageSelected);
			$('#paginationForm').submit();
		}
	}
});

});

</script>
