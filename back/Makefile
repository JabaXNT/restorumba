# Variables representing groups of services
db = db_notification db_user db_food db_redis
kafka-service = zookeeper kafka kafka-ui
notification-service = notification-microservice db_notification
user-service = user-microservice db_user
food-service = food-microservice db_food db_redis

# Phony targets
.PHONY: up-db up-kafka up-notification up-user up-food

up-db:
	@echo "Starting up the database services..."
	@docker compose down ${db}
	@docker compose up ${db} -d

up-kafka:
	@echo "Starting up the Kafka services..."
	@docker compose down ${kafka-service}
	@docker compose up ${kafka-service} -d

up-notification:
	@echo "Starting up the notification services..."
	@docker compose down ${notification-service}
	@docker compose up ${notification-service} -d

up-user:
	@echo "Starting up the user services..."
	@docker compose down ${user-service}
	@docker compose up ${user-service} -d

up-food:
	@echo "Starting up the food services..."
	@docker compose down ${food-service}
	@docker compose up ${food-service} -d
down-all:
	@echo "Shutting down all services..."
	@docker compose down
up-all:
	@echo "Starting up all services..."
	@docker compose down
	@docker compose up -d