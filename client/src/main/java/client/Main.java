package client;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuideGrpc;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Iterator;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

@Command(name = "demo", description = "...", mixinStandardHelpOptions = true)
public class Main implements Runnable {

    @Inject
    RouteGuideGrpc.RouteGuideFutureStub futureStub;

    @Inject
    RouteGuideGrpc.RouteGuideBlockingStub blockingStub;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) {
        PicocliRunner.run(Main.class, args);
    }

    @ExecuteOn(TaskExecutors.IO)
    public void run() {
        getFeature(409146138, -746188906);
        getFeature(0, 0);
        listFeatures(400000000, -750000000, 420000000, -730000000);
    }

    void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon) {
        Rectangle request =
                Rectangle.newBuilder()
                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features = blockingStub.listFeatures(request);
        for (int i = 1; features.hasNext(); i++) {
            Feature feature = features.next();
            System.out.println("Result #" + i + ": " + feature);
        }
    }

    private void getFeature(int latitude, int longitude) {
        Point request = Point.newBuilder().setLatitude(latitude).setLongitude(longitude).build();

        ListenableFuture<Feature> feature = futureStub.getFeature(request);
        Futures.addCallback(
                feature,
                new FutureCallback<Feature>() {
                    @Override
                    public void onSuccess(@NullableDecl Feature result) {
                        System.out.println(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                },
                directExecutor()
        );
    }
}