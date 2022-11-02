package com;
import java.math.BigInteger;
public class HomomorphicDecryption {
	static private BigInteger p, q, lambda;
	static public BigInteger n;
	static public BigInteger nsquare;
	static private BigInteger g;
public static void KeyGeneration() {
	n = new BigInteger("5209935468252426244906168976236492798472480324896944574739117200686986142896826797709880496714150297318988022948809468388693792207983833619963001111963867");
	nsquare = new BigInteger("27143427583354627916845127171708935172399251412421917711184570478004519747428903887003817340194794421083792795384699640411328032870422726995099527579849510837767346279084881658584935665466683182030013040542601142703761446581044190740521565737147778553839441192124818060868025680465180077332990305641513593689");
	lambda = new BigInteger("2604967734126213122453084488118246399236240162448472287369558600343493071448339684331275876461635492436521203654054740156535006447279020437357737405447280");
	g = new BigInteger("2");
}
public static BigInteger Decryption(BigInteger c) {
	BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
	return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
}

public static BigInteger Decryption1(BigInteger c) {
	BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
	return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).add(u).mod(n);
}
}
