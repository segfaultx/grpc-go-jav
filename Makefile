proto:
	protoc --go_out=./src --go-grpc_out=./src ./src/main/stock_service/proto/grpc_stocks_service.proto