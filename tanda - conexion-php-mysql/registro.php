<?php

require 'conexion.php';

// $id_grupo=$_POST['id_grupo'];
$nombreg=$_POST['nombreg'];
$integrantesg=$_POST['integrantesg'];
$fechaInicio=$_POST['fechaInicio'];
$intervalo=$_POST['intervalo'];
$fechaFin=$_POST['fechaFin'];
$monto=$_POST['monto'];
//$fechaRegistro=$_POST['fechaRegistro'];


$consulta="INSERT INTO grupo(nombreg,integrantesg,fechaInicio,intervalo,fechaFin,monto) VALUES('".$nombreg."','".$integrantesg."','".$fechaInicio."','".$intervalo."','".$fechaFin."','".$monto."')";

mysqli_query($conexion,$consulta) or die(mysqli_error($conexion));
mysqli_close($conexion);
  ?>