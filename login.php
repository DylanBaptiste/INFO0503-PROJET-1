

<?php
$options = ['http' =>['method'  => 'POST','header'  => 'Content-type:application/x-www-form-urlencoded','content' => 'login=toto&motDePasse=toto']];

$URL = "./test.php";
$contexte  = stream_context_create($options);

if(($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false){

    $tableau = json_decode($jsonTexte, true);
    var_dump($jsonTexte);
    if($tableau['code'] == "OK")
        echo "<p>Bravo!!!Vousêtesconnecté!!!</p>";
    else
        echo "<p>Erreurdeconnexion!L'erreurest<strong>".$tableau['message']."</strong></p>";
}
else
    echo "<p>Uneerreurestsurvenuelorsdelarécupérationdesdonnées.</p>";

?>