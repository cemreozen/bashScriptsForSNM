peerCount=$(ls -d1 */| wc -l)
for (( i=1; i <= $peerCount; i++ )); do
	cd P$i;
	peerName=P$i;
	echo log sender... $peerName
	cat scenarioScript_$peerName | java -jar SharkMessenger.jar "$peerName" 2 > "$peerName"LogSender_Logs.txt &
	cd ..;
done
