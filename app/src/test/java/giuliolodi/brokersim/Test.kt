package giuliolodi.brokersim

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(JUnitPlatform::class)
class Test : Spek ({
    describe("Qualcosa") {
        it("fa questo") {
            assertEquals(4, 2 + 2)
        }
    }
})