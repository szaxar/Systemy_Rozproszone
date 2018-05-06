package grpc.gen;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: currencies.proto")
public final class CurrencyStreamGrpc {

  private CurrencyStreamGrpc() {}

  public static final String SERVICE_NAME = "currencies.CurrencyStream";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.gen.CurrencyMsgRequest,
      grpc.gen.CurrencyMsgReturn> METHOD_SUBSCRIBE_CURRENCY_VALUE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "currencies.CurrencyStream", "subscribeCurrencyValue"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.gen.CurrencyMsgRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.gen.CurrencyMsgReturn.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CurrencyStreamStub newStub(io.grpc.Channel channel) {
    return new CurrencyStreamStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyStreamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CurrencyStreamBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyStreamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CurrencyStreamFutureStub(channel);
  }

  /**
   */
  public static abstract class CurrencyStreamImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribeCurrencyValue(grpc.gen.CurrencyMsgRequest request,
        io.grpc.stub.StreamObserver<grpc.gen.CurrencyMsgReturn> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBSCRIBE_CURRENCY_VALUE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SUBSCRIBE_CURRENCY_VALUE,
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.gen.CurrencyMsgRequest,
                grpc.gen.CurrencyMsgReturn>(
                  this, METHODID_SUBSCRIBE_CURRENCY_VALUE)))
          .build();
    }
  }

  /**
   */
  public static final class CurrencyStreamStub extends io.grpc.stub.AbstractStub<CurrencyStreamStub> {
    private CurrencyStreamStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyStreamStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyStreamStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyStreamStub(channel, callOptions);
    }

    /**
     */
    public void subscribeCurrencyValue(grpc.gen.CurrencyMsgRequest request,
        io.grpc.stub.StreamObserver<grpc.gen.CurrencyMsgReturn> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_SUBSCRIBE_CURRENCY_VALUE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CurrencyStreamBlockingStub extends io.grpc.stub.AbstractStub<CurrencyStreamBlockingStub> {
    private CurrencyStreamBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyStreamBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyStreamBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyStreamBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<grpc.gen.CurrencyMsgReturn> subscribeCurrencyValue(
        grpc.gen.CurrencyMsgRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_SUBSCRIBE_CURRENCY_VALUE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CurrencyStreamFutureStub extends io.grpc.stub.AbstractStub<CurrencyStreamFutureStub> {
    private CurrencyStreamFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyStreamFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyStreamFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyStreamFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_CURRENCY_VALUE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CurrencyStreamImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CurrencyStreamImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_CURRENCY_VALUE:
          serviceImpl.subscribeCurrencyValue((grpc.gen.CurrencyMsgRequest) request,
              (io.grpc.stub.StreamObserver<grpc.gen.CurrencyMsgReturn>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class CurrencyStreamDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.gen.Currencies.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CurrencyStreamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CurrencyStreamDescriptorSupplier())
              .addMethod(METHOD_SUBSCRIBE_CURRENCY_VALUE)
              .build();
        }
      }
    }
    return result;
  }
}
