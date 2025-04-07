peerName=$(basename $(pwd))
echo log sender... $peerName
cat scenarioScript_$peerName | java -jar SharkMessenger.jar "$peerName" 2 > "$peerName"LogSender_Logs.txt &
