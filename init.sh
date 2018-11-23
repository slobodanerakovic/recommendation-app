function cleanup_handler {
	echo "******** CLEANUP **********"
	sleep 1
	docker kill recommendation_app
	sleep 1
	docker rm recommendation_app
	sleep 1
	docker kill pg_database
	sleep 1
	docker rm pg_database
	sleep 1
	echo "******** CLEANUP FINISHED **********"
	exit
}
echo "******** DECKER DEPLOYMENT STARTED **********"
trap cleanup_handler INT

echo "******** START BUILDING MYPOSTGRES CONTAINER **********"
docker build -t mypostgres postgres/
echo "******** FINISHED BUILDING MYPOSTGRES CONTAINER **********"
sleep 3
echo "******** RUN MYPOSTGRES CONTAINER **********"
docker run -P --name pg_database -p 5432:5432 mypostgres &
sleep 5

echo "******** CHECK DOCKER IP AND UPDATE APPLICATION.YML **********"
ip=$(docker inspect -f '{{ .NetworkSettings.IPAddress }}' pg_database)
echo $ip
rpl localhost ${ip} recommendation-app/src/main/resources/application.yml

if [ -n "${ip}" ];then
  echo "******** DOCKER IP $ip **********"
else
  echo "******** DOCKER IP NONE EXISTS FOR ID pg_database **********"
fi

sleep 2
echo "******** START BUILDING RECOMMENDATION CONTAINER **********"
docker build -t recommendation-app recommendation-app/
echo "******** FINISHED BUILDING RECOMMENDATION CONTAINER **********"
sleep 3
echo "******** RUN RECOMMENDATION CONTAINER **********"
docker run -P --name recommendation_app -p 6789:6789 -t recommendation-app

