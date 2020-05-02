package noc.router

class Router() extends Module(){
	val io = IO(new Bundle{
    val dIn = Input(UInt(105))
    val dOut = Output(UInt(105))
	})

    val idle :: checkHeader :: abstractRoute :: packetOut :: sendData :: Nil = Enum(5)
    stateReg = RegInit(idle)

    val d1Reg = RegInit(0.U(35))
    val d2Reg = RegInit(0.U(35))
    val d3Reg = RegInit(0.U(35))
    val dOut1Reg = RegInit(0.U(35))
    val dOut2Reg = RegInit(0.U(35))
    val dOut3Reg = RegInit(0.U(35))
    val pType1Reg = RegInit(0.U(3))
    val pType2Reg = RegInit(0.U(3))
    val pType3Reg = RegInit(0.U(3))
    val headerReg = RegInit(0.U(35))
    val routeReg = RegInit(0.U(16))
    val sHeaderReg = RegInit(0.U(19))


    when (stateReg === idle) {
        when(io.dIn) {
           stateReg := checkHeader
           d1Reg := io.dIn(34,0)
           d2Reg := io.dIn(69,35)
           d3Reg := io.dIn(104,70)
        }
    } .elsewhen (stateReg === checkHeader) {
        pType1Reg := d1Reg(2,0)
        pType2Reg := d2Reg(2,0)
        pType3Reg := d3Reg(2,0)
    
         if(pType1Reg === Bits("b001"))
            headerReg := d1Reg
    
        stateReg := abstractRoute
    } .elsewhen(stateReg === abstractRoute){
        routeReg = headerReg(34,19)
        sHeaderReg = headerReg(18,0)

        if(xBarReg === Bits("b00"))
            east
        if(xBarReg === Bits("b01"))
            south
        if(xBarReg === Bits("b10"))
            west
        if(xBarReg === Bits("b11"))
            north
        routeReg = routeReg >> 2
        dOut1Reg = routeReg | sHeaderReg

        stateReg := sendData
    } .elsewhen(stateReg === sendData) {
        dOut1Reg := io.dOut(34,0)
        dOut2Reg := io.dOut(69,35)
        dOut3Reg := io.dOut(104,70)
    } .otherwise {
        //
    }
}
