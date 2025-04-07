### Variables

hostAddress=localhost
datum=$(date +"%y-%m-%d(%H:%M:%S)")
peerCount=$1
peerName=$(basename $(pwd)) # how?????

### Test folder ###

mkdir "$datum"
cd "$datum"
for (( i=1; i <= $peerCount; i++ )); do
	mkdir P"$i";
	cp ~/Desktop/bashScriptsSNM/receiveCommands.txt P$i;
	cp ~/Desktop/bashScriptsSNM/SharkMessenger.jar P$i;
	cp ~/Desktop/bashScriptsSNM/sendLogs.sh P$i;
	cd P$i;
	cat receiveCommands.txt | java -jar SharkMessenger.jar P"$i" 2 > MessengerLogs_P"$i".txt &
	cd ..;
done
wait

for (( i=1; i <= $peerCount; i++ )); do
	cd P$i;
	./sendLogs.sh &
	cd ..;
done
echo current dir: $(pwd) receiverSkript done
