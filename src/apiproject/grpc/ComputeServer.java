package apiproject.grpc;

import apiproject.ComputationAPIImpl;
import apiproject.UserComputeEngineAPIImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class ComputeServer {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ComputeServiceImpl())
                .build()
                .start();

        System.out.println("Compute server running on port 50051");
        Runtime.getRuntime().addShutdownHook(
                new Thread(server::shutdown));
        server.awaitTermination();
    }

    static class ComputeServiceImpl
            extends ComputeServiceGrpc.ComputeServiceImplBase {

        @Override
        public void configureJob(
                JobRequest request,
                StreamObserver<JobResponse> observer) {

            String input = request.getInputSource();
            String output = request.getOutputDestination();
            String delimiterText = request.getDelimiter();

            if (input.isBlank() || output.isBlank()
                    || delimiterText.isEmpty()) {
                observer.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid job configuration")
                        .asRuntimeException());
                return;
            }

            try (StorageClient storage =
                         new StorageClient("localhost", 50052)) {

                storage.setOutputDestination(output);

                UserComputeEngineAPIImpl engine =
                        new UserComputeEngineAPIImpl(
                                storage, new ComputationAPIImpl());

                engine.configureJob(
                        input, output, delimiterText.charAt(0));

                String status = engine.getLastJobStatus();

                observer.onNext(JobResponse.newBuilder()
                        .setSuccess("SUCCESS".equals(status))
                        .setMessage(status)
                        .build());
                observer.onCompleted();

            } catch (RuntimeException e) {
                observer.onError(Status.INTERNAL
                        .withDescription("Compute server error")
                        .asRuntimeException());
            }
        }
    }
}
