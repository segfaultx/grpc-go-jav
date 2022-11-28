package main

import (
	"flag"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"math/rand"
	"net"
	"time"

	pb "com.example/grpctest/server"
)

var (
	port = flag.Int("port", 50051, "The go_server port")
)

type server struct {
	pb.UnimplementedStockServiceServer
}

func (s *server) GetStockUpdates(in *pb.StockUpdateRequest, stream pb.StockService_GetStockUpdatesServer) error {
	log.Printf("Received: %v", in.GetName())

	for {
		time.Sleep(time.Duration(rand.Int31n(5000)) * time.Millisecond)
		err := stream.Send(&pb.StockUpdateResponse{StockValue: rand.Int31(), Name: in.GetName()})
		if err != nil {
			break
		}
	}
	return nil
}

func main() {
	flag.Parse()
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", *port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterStockServiceServer(s, &server{})
	log.Printf("go_server listening at %v", lis.Addr())
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
