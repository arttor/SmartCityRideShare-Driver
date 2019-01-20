package com.tlabs.smartcity.rideshare.ridesharedriver.data

import java.util.*

data class LoginReq(val password:String, val username: String)
data class LoginResp(val token:String)
data class CreateConnOfferResp(val message:CreateConnOfferRespMsg)
data class CreateConnOfferRespMsg(val message:CreateConnOfferRespMsgMsg,
                                  val id: String, val type:String)
data class CreateConnOfferRespMsgMsg(
    val did:String,
    val verkey:String,
    val endpoint:String,
    val nonce:String,
    val data:ConOfferData
)
data class PairwiseConnResp(val pairwise: List<Pairwise>)
data class Pairwise(val their_did: String)
data class CreateProofReq(
    val recipientDid: String,
    val proofRequest: ProofRequest
)
data class ProofRequest(
    val name: String = "PhoneNumberVerify",
    val version: String = "1.0",
    val requested_attributes: RequestedAttributes = RequestedAttributes()
)
data class RequestedAttributes(
    val attr1_referent: Attr1Referent = Attr1Referent()
)
data class Attr1Referent(
    val name: String = "phone_number@string",
    val restrictions: List<Restriction> = Collections.singletonList(Restriction())
)
data class Restriction(
    val cred_def_id: String = "Gc3HWtzjBuaGyMkSHgomzx:3:CL:18:jesse-credential-def2" // TODO: TBD
)
data class RequestedPredicates (val id:String,
                           val proof: Proof)

data class Proof(val requested_proof:ReqProof)
data class ReqProof(val revealed_attrs:RevAttrs)
data class RevAttrs(val attr1_referent:Attr1Ref)
data class Attr1Ref(val raw:String)
data class AccAndCreProof(val proofRequestId:String)
data class GetProofReqResp(val id:String)
data class ConOfferData(val app: String = "<your-app-or-service-name>")
data class SovrinReq(val connectionOffer:CreateConnOfferRespMsg)

