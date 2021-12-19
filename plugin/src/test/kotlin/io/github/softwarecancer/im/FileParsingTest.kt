package io.github.softwarecancer.im

import java.io.File
import kotlin.test.Test

class FileParsingTest {

  @Test
  fun `check how tsv parsing works`() {
    processCalibrationFile(File("src/test/resources/calibrations/calibration-1d-2.2.8.txt"))
  }
}
