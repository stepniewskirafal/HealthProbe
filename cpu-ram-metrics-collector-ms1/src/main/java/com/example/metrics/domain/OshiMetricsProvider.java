package com.example.metrics.domain;

import java.net.InetAddress;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import org.example.SystemMetrics;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

@Service
public class OshiMetricsProvider implements MetricsProvider {

  private final SystemInfo si = new SystemInfo();
  private final CentralProcessor processor = si.getHardware().getProcessor();
  private final GlobalMemory memory = si.getHardware().getMemory();

  // do liczenia zużycia CPU pomiędzy kolejnymi „tickami”
  private final AtomicReference<long[]> prevTicksRef =
      new AtomicReference<>(processor.getSystemCpuLoadTicks());

  private final String hostId = resolveHostId();
  private final double cpuPowerGhz = round2(detectCpuPowerGhz()); // stała wartość, liczona raz

  @Override
  public SystemMetrics getMetrics() {
    // CPU
    double cpuUsagePct = cpuLoadPct();

    // RAM
    long totalBytes = memory.getTotal();
    long availBytes = memory.getAvailable();
    long usedBytes = totalBytes - availBytes;

    double totalGb = bytesToGb(totalBytes);
    double ramUsagePct = totalBytes > 0 ? (usedBytes * 100.0 / totalBytes) : 0.0;

    return new SystemMetrics(
        hostId,
        Instant.now(),
        cpuPowerGhz,
        round2(cpuUsagePct),
        round2(totalGb),
        round2(ramUsagePct));
  }

  private double cpuLoadPct() {
    long[] prev = prevTicksRef.get();
    double load = processor.getSystemCpuLoadBetweenTicks(prev) * 100.0;
    prevTicksRef.set(processor.getSystemCpuLoadTicks());
    if (Double.isNaN(load) || load < 0) return 0.0;
    return Math.min(load, 100.0);
  }

  private double detectCpuPowerGhz() {
    long maxFreq = processor.getMaxFreq(); // Hz
    if (maxFreq <= 0) {
      long vendor = processor.getProcessorIdentifier().getVendorFreq(); // Hz
      maxFreq = Math.max(0, vendor);
    }
    return maxFreq / 1_000_000_000.0;
  }

  private static double bytesToGb(long bytes) {
    return bytes / 1024.0 / 1024.0 / 1024.0;
  }

  private static double round2(double v) {
    return Math.round(v * 100.0) / 100.0;
  }

  private static String resolveHostId() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      return "unknown-host";
    }
  }
}
