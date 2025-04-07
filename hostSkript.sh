peerCount=$1
hostAddress="localhost"

for (( i=1; i<=$peerCount; i++ )); do
	echo "sendMessage asapLogsP$i.txt" > scenarioScript_P$i;
	echo "sendMessage MessengerLog_P$i.txt" >> scenarioScript_P$i;
	echo "connectTCP $hostAddress 9999" >> scenarioScript_P$i;
	echo "wait 500" >> scenarioScript_P$i;
	echo "exit" >> scenarioScript_P$i;
	echo "sendMessage scenarioScript_P$i sn/file" >> hostCommandlist.txt;
done

echo "openTCP 9999" >> hostCommandlist.txt;
echo "wait $(($peerCount*1000))" >> hostCommandlist.txt;
#echo lsMessages >> hostCommandlist.txt;
echo "wait $(($peerCount*1500))" >> hostCommandlist.txt;
echo lsMessages >> hostCommandlist.txt;
echo exit >> hostCommandlist.txt;

echo receive starts:
./receiveScenario.sh $peerCount &
echo host starts:
cat hostCommandlist.txt | java -jar SharkMessenger.jar TestHost 2 > hostLog.txt &
time sleep 10
wait
echo "test done, cat hostLog.txt to see messenger logs"
