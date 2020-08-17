<?php
require 'conexion.php';

$telefono=$_GET['telefono'];
///$telefono="5547113677"

$consulta = "SELECT grupo.id_grupo, grupo.nombreg, grupo.integrantesg, grupo.fechaInicio, grupo.intervalo, grupo.fechaFin, grupo.monto, grupo.imageView
	FROM grupo 
	INNER JOIN inte_grupo ON grupo.id_grupo = inte_grupo.id_grupo
	WHERE inte_grupo.telefono LIKE '%$telefono%'";

$resultado = $conexion -> query($consulta);

while ($fila=$resultado -> fetch_array()){
	$grupo[] = array_map("utf8_decode", $fila);
}

echo json_encode($grupo);
$resultado ->close();

?>