peerCount=$1
hostAddress="localhost"

for (( i=1; i<=$peerCount; i++ )); do
	echo "sendMessage MessengerLog_P$i.txt sn/file" >> scenarioScript_P$i;
	echo wait 500 >> scenarioScript_P$i;
	echo "sendMessage asapLogsP$i.txt sn/file" >> scenarioScript_P$i;
	echo "markstep sentLogs" >> scenarioScript_P$i;
	echo "lsMessages" >> scenarioScript_P$i;
	echo "connectTCP $hostAddress 9999" >> scenarioScript_P$i;
	echo "wait 1000" >> scenarioScript_P$i;
	echo "exit" >> scenarioScript_P$i;
	echo "sendMessage scenarioScript_P$i sn/file" >> hostCommandlist.txt;
done

echo "openTCP 9999" >> hostCommandlist.txt;
echo "wait $(($peerCount*3000))" >> hostCommandlist.txt;
#echo lsMessages >> hostCommandlist.txt;
echo "wait $(($peerCount*3000))" >> hostCommandlist.txt;
echo lsMessages >> hostCommandlist.txt;
echo wait 10000 >> hostCommandlist.txt;
echo exit >> hostCommandlist.txt;

echo receive starts:
./receiveScenario.sh $peerCount &
echo host starts:
./startHost.sh $peerCount &
sleep 2
wait
echo "test done, cat hostLog.txt to see messenger logs"
