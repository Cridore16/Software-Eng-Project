package apiproject.grpc;

import apiproject.DataStorageAPIImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class StorageServer {

        public static void main(String[] args) throws Exception {
                Server server = ServerBuilder.forPort(50052)
                                .addService(new StorageServiceImpl())
                                .build()
                                .start();

                System.out.println("Storage server running on port 50052");

                Runtime.getRuntime().addShutdownHook(
                                new Thread(server::shutdown));

                server.awaitTermination();
        }

        static class StorageServiceImpl
                        extends StorageServiceGrpc.StorageServiceImplBase {

                @Override
                public void readInputData(
                                ReadRequest request,
                                StreamObserver<ReadResponse> observer) {

                        try {
                                DataStorageAPIImpl storage = new DataStorageAPIImpl();

                                java.util.List<Integer> numbers = storage.readInputData(request.getInput());

                                if (numbers == null) {
                                        observer.onError(
                                                        io.grpc.Status.INVALID_ARGUMENT
                                                                        .withDescription("Unable to read input")
                                                                        .asRuntimeException());
                                        return;
                                }

                                ReadResponse response = ReadResponse.newBuilder()
                                                .addAllNumbers(numbers)
                                                .build();

                                observer.onNext(response);
                                observer.onCompleted();

                        } catch (RuntimeException e) {
                                observer.onError(
                                                io.grpc.Status.INTERNAL
                                                                .withDescription("Storage read failed")
                                                                .asRuntimeException());
                        }
                }

                @Override
                public void writeOutputData(
                                WriteRequest request,
                                StreamObserver<WriteResponse> observer) {

                        try {
                                DataStorageAPIImpl storage = new DataStorageAPIImpl(request.getDestination());

                                storage.writeOutputData(request.getOutput());

                                boolean success = storage.wasLastWriteSuccessful();

                                WriteResponse response = WriteResponse.newBuilder()
                                                .setSuccess(success)
                                                .setMessage(success ? "Saved" : "Write failed")
                                                .build();

                                observer.onNext(response);
                                observer.onCompleted();

                        } catch (RuntimeException e) {
                                observer.onError(
                                                io.grpc.Status.INTERNAL
                                                                .withDescription("Storage write failed")
                                                                .asRuntimeException());
                        }
                }
        }
}
