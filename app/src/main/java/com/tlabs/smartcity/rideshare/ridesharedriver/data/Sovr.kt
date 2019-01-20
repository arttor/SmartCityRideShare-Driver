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
    val nonce:String
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
    val name: String = "prone_number@string",
    val restrictions: List<Restriction> = Collections.singletonList(Restriction())
)
data class Restriction(
    val cred_def_id: String = "" // TODO: TBD
)
data class RequestedPredicates (val id:String,
                           val proof: Proof)

data class Proof(val requested_proof:ReqProof)
data class ReqProof(val revealed_attrs:RevAttrs)
data class RevAttrs(val attr1_referent:Attr1Ref)
data class Attr1Ref(val raw:String)
data class AccAndCreProof(val proofRequestId:String)
data class GetProofReqResp(val id:String)
data class SovrinReq(val connectionOffer:CreateConnOfferRespMsg)

