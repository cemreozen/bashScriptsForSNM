### Variables

hostAddress=localhost
datum=$(date +"%y-%m-%d(%H:%M:%S)")
peerCount=$1
peerName=$(basename $(pwd)) # how?????

### Test folder ###

mkdir "$datum"
cp ./sendLogs.sh $datum;
cd "$datum"
for (( i=1; i <= $peerCount; i++ )); do
	mkdir P"$i";
	cp ../receiveCommands.txt P$i;
	cp ../SharkMessenger.jar P$i;
	cd P$i;
	cat receiveCommands.txt | java -jar SharkMessenger.jar P"$i" 2 > MessengerLog_P"$i".txt &
	cd ..;
done
sleep 3
echo current dir: $(pwd) receiverSkript done
wait
sleep 3
./sendLogs.sh;
echo done;
