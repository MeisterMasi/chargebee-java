package com.chargebee.models;

import com.chargebee.*;
import com.chargebee.internal.*;
import com.chargebee.filters.*;
import com.chargebee.filters.enums.SortOrder;
import com.chargebee.internal.HttpUtil.Method;
import com.chargebee.models.enums.*;
import org.json.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Transaction extends Resource<Transaction> {

    public enum Type {
        AUTHORIZATION,
        PAYMENT,
        REFUND,
        PAYMENT_REVERSAL,
        _UNKNOWN; /*Indicates unexpected value for this enum. You can get this when there is a
        java-client version incompatibility. We suggest you to upgrade to the latest version */
    }

    public enum Status {
        IN_PROGRESS,
        SUCCESS,
        VOIDED,
        FAILURE,
        TIMEOUT,
        NEEDS_ATTENTION,
        _UNKNOWN; /*Indicates unexpected value for this enum. You can get this when there is a
        java-client version incompatibility. We suggest you to upgrade to the latest version */
    }

    public enum FraudFlag {
        SAFE,
        SUSPICIOUS,
        FRAUDULENT,
        _UNKNOWN; /*Indicates unexpected value for this enum. You can get this when there is a
        java-client version incompatibility. We suggest you to upgrade to the latest version */
    }

    public static class LinkedInvoice extends Resource<LinkedInvoice> {
        public LinkedInvoice(JSONObject jsonObj) {
            super(jsonObj);
        }

        public String invoiceId() {
            return reqString("invoice_id");
        }

        public Integer appliedAmount() {
            return reqInteger("applied_amount");
        }

        public Timestamp appliedAt() {
            return reqTimestamp("applied_at");
        }

        public Timestamp invoiceDate() {
            return optTimestamp("invoice_date");
        }

        public Integer invoiceTotal() {
            return optInteger("invoice_total");
        }

        public Invoice.Status invoiceStatus() {
            return reqEnum("invoice_status", Invoice.Status.class);
        }

    }

    public static class LinkedCreditNote extends Resource<LinkedCreditNote> {
        public LinkedCreditNote(JSONObject jsonObj) {
            super(jsonObj);
        }

        public String cnId() {
            return reqString("cn_id");
        }

        public Integer appliedAmount() {
            return reqInteger("applied_amount");
        }

        public Timestamp appliedAt() {
            return reqTimestamp("applied_at");
        }

        public CreditNote.ReasonCode cnReasonCode() {
            return reqEnum("cn_reason_code", CreditNote.ReasonCode.class);
        }

        public Timestamp cnDate() {
            return optTimestamp("cn_date");
        }

        public Integer cnTotal() {
            return optInteger("cn_total");
        }

        public CreditNote.Status cnStatus() {
            return reqEnum("cn_status", CreditNote.Status.class);
        }

        public String cnReferenceInvoiceId() {
            return reqString("cn_reference_invoice_id");
        }

    }

    public static class LinkedRefund extends Resource<LinkedRefund> {
        public LinkedRefund(JSONObject jsonObj) {
            super(jsonObj);
        }

        public String txnId() {
            return reqString("txn_id");
        }

        public Transaction.Status txnStatus() {
            return reqEnum("txn_status", Transaction.Status.class);
        }

        public Timestamp txnDate() {
            return reqTimestamp("txn_date");
        }

        public Integer txnAmount() {
            return reqInteger("txn_amount");
        }

    }

    //Constructors
    //============

    public Transaction(String jsonStr) {
        super(jsonStr);
    }

    public Transaction(JSONObject jsonObj) {
        super(jsonObj);
    }

    // Fields
    //=======

    public String id() {
        return reqString("id");
    }

    public String customerId() {
        return optString("customer_id");
    }

    public String subscriptionId() {
        return optString("subscription_id");
    }

    public String gatewayAccountId() {
        return optString("gateway_account_id");
    }

    public String paymentSourceId() {
        return optString("payment_source_id");
    }

    public PaymentMethod paymentMethod() {
        return reqEnum("payment_method", PaymentMethod.class);
    }

    public String referenceNumber() {
        return optString("reference_number");
    }

    public Gateway gateway() {
        return reqEnum("gateway", Gateway.class);
    }

    public Type type() {
        return reqEnum("type", Type.class);
    }

    public Timestamp date() {
        return optTimestamp("date");
    }

    public String currencyCode() {
        return reqString("currency_code");
    }

    public Integer amount() {
        return optInteger("amount");
    }

    public String idAtGateway() {
        return optString("id_at_gateway");
    }

    public Status status() {
        return optEnum("status", Status.class);
    }

    public FraudFlag fraudFlag() {
        return optEnum("fraud_flag", FraudFlag.class);
    }

    public String errorCode() {
        return optString("error_code");
    }

    public String errorText() {
        return optString("error_text");
    }

    public Timestamp voidedAt() {
        return optTimestamp("voided_at");
    }

    public Long resourceVersion() {
        return optLong("resource_version");
    }

    public Timestamp updatedAt() {
        return optTimestamp("updated_at");
    }

    public String fraudReason() {
        return optString("fraud_reason");
    }

    public Integer amountUnused() {
        return optInteger("amount_unused");
    }

    public String maskedCardNumber() {
        return optString("masked_card_number");
    }

    public String referenceTransactionId() {
        return optString("reference_transaction_id");
    }

    public String refundedTxnId() {
        return optString("refunded_txn_id");
    }

    public String reversalTransactionId() {
        return optString("reversal_transaction_id");
    }

    public List<Transaction.LinkedInvoice> linkedInvoices() {
        return optList("linked_invoices", Transaction.LinkedInvoice.class);
    }

    public List<Transaction.LinkedCreditNote> linkedCreditNotes() {
        return optList("linked_credit_notes", Transaction.LinkedCreditNote.class);
    }

    public List<Transaction.LinkedRefund> linkedRefunds() {
        return optList("linked_refunds", Transaction.LinkedRefund.class);
    }

    public Boolean deleted() {
        return reqBoolean("deleted");
    }

    // Operations
    //===========

    public static TransactionListRequest list() throws IOException {
        String uri = uri("transactions");
        return new TransactionListRequest(uri);
    }

    @Deprecated
    public static ListRequest transactionsForCustomer(String id) throws IOException {
        String uri = uri("customers", nullCheck(id), "transactions");
        return new ListRequest(uri);
    }

    @Deprecated
    public static ListRequest transactionsForSubscription(String id) throws IOException {
        String uri = uri("subscriptions", nullCheck(id), "transactions");
        return new ListRequest(uri);
    }

    public static ListRequest paymentsForInvoice(String id) throws IOException {
        String uri = uri("invoices", nullCheck(id), "payments");
        return new ListRequest(uri);
    }

    public static Request retrieve(String id) throws IOException {
        String uri = uri("transactions", nullCheck(id));
        return new Request(Method.GET, uri);
    }


    // Operation Request Classes
    //==========================

    public static class TransactionListRequest extends ListRequest<TransactionListRequest> {

        private TransactionListRequest(String uri) {
            super(uri);
        }
    
        public TransactionListRequest includeDeleted(Boolean includeDeleted) {
            params.addOpt("include_deleted", includeDeleted);
            return this;
        }


        public StringFilter<TransactionListRequest> id() {
            return new StringFilter<TransactionListRequest>("id",this).supportsMultiOperators(true);        
        }


        public StringFilter<TransactionListRequest> customerId() {
            return new StringFilter<TransactionListRequest>("customer_id",this).supportsMultiOperators(true).supportsPresenceOperator(true);        
        }


        public StringFilter<TransactionListRequest> subscriptionId() {
            return new StringFilter<TransactionListRequest>("subscription_id",this).supportsMultiOperators(true).supportsPresenceOperator(true);        
        }


        public StringFilter<TransactionListRequest> paymentSourceId() {
            return new StringFilter<TransactionListRequest>("payment_source_id",this).supportsMultiOperators(true).supportsPresenceOperator(true);        
        }


        public EnumFilter<com.chargebee.models.enums.PaymentMethod, TransactionListRequest> paymentMethod() {
            return new EnumFilter<com.chargebee.models.enums.PaymentMethod, TransactionListRequest>("payment_method",this);        
        }


        public EnumFilter<com.chargebee.models.enums.Gateway, TransactionListRequest> gateway() {
            return new EnumFilter<com.chargebee.models.enums.Gateway, TransactionListRequest>("gateway",this);        
        }


        public StringFilter<TransactionListRequest> gatewayAccountId() {
            return new StringFilter<TransactionListRequest>("gateway_account_id",this).supportsMultiOperators(true);        
        }


        public StringFilter<TransactionListRequest> idAtGateway() {
            return new StringFilter<TransactionListRequest>("id_at_gateway",this);        
        }


        public StringFilter<TransactionListRequest> referenceNumber() {
            return new StringFilter<TransactionListRequest>("reference_number",this).supportsPresenceOperator(true);        
        }


        public EnumFilter<Transaction.Type, TransactionListRequest> type() {
            return new EnumFilter<Transaction.Type, TransactionListRequest>("type",this);        
        }


        public TimestampFilter<TransactionListRequest> date() {
            return new TimestampFilter<TransactionListRequest>("date",this);        
        }


        public NumberFilter<Integer, TransactionListRequest> amount() {
            return new NumberFilter<Integer, TransactionListRequest>("amount",this);        
        }


        public EnumFilter<Transaction.Status, TransactionListRequest> status() {
            return new EnumFilter<Transaction.Status, TransactionListRequest>("status",this);        
        }


        public TimestampFilter<TransactionListRequest> updatedAt() {
            return new TimestampFilter<TransactionListRequest>("updated_at",this);        
        }


        public TransactionListRequest sortByDate(SortOrder order) {
            params.addOpt("sort_by["+order.name().toLowerCase()+"]","date");
            return this;
        }


        @Override
        public Params params() {
            return params;
        }
    }

}
