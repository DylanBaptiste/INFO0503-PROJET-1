<form enctype="application/json" action="./login.php" method="post">
<fieldset>
    <legend>Formulaire</legend>
        <p>
            <label>Envoyer le fichier :</label>
            <input name="data" type="text" value='{"login": "toto", "mdp": "toto"}'/>
            <input type="submit" name="submit" value="tester" />
        </p>
</fieldset>
</form>

<?php

header("Content-type : application/json");

/*
{"login": "toto", "mdp": "toto"}
"{\"login\":\"toto\",\"mdp\":\"monmdp\"}"
"{\"data\":{\"login\":\"toto\",\"mdp\":\"monmdp\"}}"
*/
if(isset($_POST['data'])) {
    $tableauRecu = json_decode($_POST['data'], true);
    echo print_r($tableauRecu);
    if(isset($tableauRecu['login']) && isset($tableauRecu['mdp'])) {
        
        if(($tableauRecu['login'] != "") && ($tableauRecu['mdp'] != "")) {
            //$tableau = array("code" => "OK", "message" => "login correct");
           
                $tableau = array("login" => $tableauRecu['login'], "mdp" => $tableauRecu['mdp']);
   
                // Préparation des données pour la requête
                $data = http_build_query(
                        array(
                            'data' => json_encode($tableau)
                        )
                    );

                // Préparation de la requête
                $options = array('http' =>
                        array(
                            'method'  => 'POST',
                            'header'  => 'Content-type: application/x-www-form-urlencoded',
                            'content' => $data
                        )
                    );

                // Envoi de la requête et lecture du JSON reçu

                $contexte  = stream_context_create($options);
                $jsonTexte = file_get_contents('http://localhost/projet0503/INFO0503-PROJET-1/APPLI_JAVA.php', false, $contexte);
                
                echo("<br><br>recus par la requete: <br>");
                echo "<br><br>text brut:";
                var_dump($jsonTexte);

                echo "<br><br>tableau genéré:";
                $tableau = json_decode($jsonTexte, true);
                var_dump($tableau);

        }
        else {
            $tableau = array("code" => "erreur", "message" => "login incorrect");
            //login incorect
        }
    }
    else {
        //pas de donnee
        $tableau = array("code" => "erreur", "message" => "données manquantes.");
    }
}
else {
    //donnee mal formé
    $tableau = array("code" => "erreur", "message" => "pas de données en POST.");
}