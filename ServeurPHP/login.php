<?php
header("Content-type:application/json; utf-8");

include("api.php");
$api = new api();

$data = json_decode(file_get_contents('php://input'), true);

if( isset($data) )
{
    if(isset($data['action']))
    {
        switch ($data['action']) {
            case "1":
                if( isset($data['login']) && isset($data['password']) )
                    echo $api->login($data['login'], $data['password']);
                else
                    echo $api->error("Données de connexion non fournis");
                break;

            case "2":
                echo $api->error("Action non implémenté");
                break;

            default:
                echo $api->error("Cette action n'existe pas");
                break;
        }
    }
    else
    {
        echo $api->error("Aucune action choisie.");
    }

}
else
{
    echo $api->error('Aucune données envoyées');
}



?>
