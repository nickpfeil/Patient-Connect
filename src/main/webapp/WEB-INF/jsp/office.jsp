<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<link rel="stylesheet"
	href="/maps/documentation/javascript/cgc/demos.css">


<h3>View Office information Below:</h3>


<div id="map"></div>
<script>
      function initMap() {
    	  //map option
    	  var options = {
    		zoom:8,
    		center:{lat:41.4993,lng:-81.6944}
    	  
    	  }
    	  //new map
          var map = new 
          google.maps.Map(document.getElementById('map'),options);
    	  
    	  //add marker
        var marker = new google.maps.Marker({
            position: {lat:41.4993,lng:-81.6944},
            map:map,
/*             icon:
 */
        });
    	  var infoWindow = new google.maps.infoWindow({
    	  });
      }
      
 
      
    </script>


<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsZpASAimO5p-PdKfXQaNDwTW05OPXbbc 
&callback=initMap"
	async defer></script>
</body>
</html>

















<c:import url="/WEB-INF/jsp/footer.jsp" />
